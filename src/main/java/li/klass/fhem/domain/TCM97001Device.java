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

package li.klass.fhem.domain;

import li.klass.fhem.domain.core.DeviceFunctionality;
import li.klass.fhem.domain.core.FhemDevice;
import li.klass.fhem.domain.core.XmllistAttribute;
import li.klass.fhem.domain.genericview.ShowField;
import li.klass.fhem.resources.ResourceIdMapper;

public class TCM97001Device extends FhemDevice<TCM97001Device> {
    @ShowField(description = ResourceIdMapper.battery)
    @XmllistAttribute("battery")
    private String battery;

    @ShowField(description = ResourceIdMapper.temperature)
    @XmllistAttribute("TEMPERATURE")
    private String temperature;

    public String getBattery() {
        return battery;
    }

    public String getTemperature() {
        return temperature;
    }

    @Override
    public DeviceFunctionality getDeviceGroup() {
        return DeviceFunctionality.TEMPERATURE;
    }

    @Override
    public boolean isSupported() {
        return super.isSupported() && temperature != null;
    }

}
