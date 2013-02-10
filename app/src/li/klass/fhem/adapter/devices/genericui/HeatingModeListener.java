/*
 * AndFHEM - Open Source Android application to control a FHEM home automation
 * server.
 *
 * Copyright (c) 2011, Matthias Klass or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU GENERAL PUBLIC LICENSE, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU GENERAL PUBLIC LICENSE
 * for more details.
 *
 * You should have received a copy of the GNU GENERAL PUBLIC LICENSE
 * along with this distribution; if not, write to:
 *   Free Software Foundation, Inc.
 *   51 Franklin Street, Fifth Floor
 *   Boston, MA  02110-1301  USA
 */

package li.klass.fhem.adapter.devices.genericui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.TableLayout;
import android.widget.TableRow;
import li.klass.fhem.AndFHEMApplication;
import li.klass.fhem.R;
import li.klass.fhem.adapter.devices.core.FieldNameAddedToDetailListener;
import li.klass.fhem.constants.Actions;
import li.klass.fhem.constants.BundleExtraKeys;
import li.klass.fhem.domain.core.Device;
import li.klass.fhem.domain.heating.HeatingModeDevice;
import li.klass.fhem.util.EnumUtils;

import static li.klass.fhem.util.EnumUtils.toStringList;

public class HeatingModeListener<D extends Device<D> & HeatingModeDevice<M>, M extends Enum<M>> extends FieldNameAddedToDetailListener<D> {

    @Override
    public void onFieldNameAdded(Context context, TableLayout tableLayout, String field, D device, TableRow fieldTableRow) {
        if (!doAddField(device)) return;

        M mode = device.getHeatingMode();
        int selected = EnumUtils.positionOf(device.getHeatingModes(), mode);

        tableLayout.addView(new SpinnerActionRow<D>(context, R.string.mode, R.string.setMode, toStringList(device.getHeatingModes()), selected) {

            @Override
            public void onItemSelected(final Context context, D device, String item) {
                M mode = EnumUtils.valueOf(device.getHeatingModes(), item);

                if (mode == getUnknownMode() || mode == null) {
                    revertSelection();
                    return;
                }

                changeMode(mode, device, context);
            }
        }.createRow(device));
    }

    private void putUpdateIntent(Intent intent) {
        intent.putExtra(BundleExtraKeys.RESULT_RECEIVER, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                AndFHEMApplication.getContext().sendBroadcast(new Intent(Actions.DO_UPDATE));
            }
        });
    }

    protected void changeMode(M newMode, D device, Context context) {
        final Intent intent = new Intent(Actions.DEVICE_SET_MODE);
        intent.putExtra(BundleExtraKeys.DEVICE_NAME, device.getName());
        intent.putExtra(BundleExtraKeys.DEVICE_MODE, newMode);
        putUpdateIntent(intent);

        context.startService(intent);
    }

    protected M getUnknownMode() {
        return null;
    }

    protected boolean doAddField(D device) {
        return true;
    }
}
