// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.gui.icons;

import javax.swing.*;

public enum Icon {
    MADX("madx.png"), //
    JMAD("jmad.png"), //
    SPLASH("jmad-splash.jpg"), //
    REFRESH("refresh.png"), //
    DELETE("delete.png"), //
    CHART("chart.png"), //
    EXIT("exit.gif"), //
    EXPORT("export.png"), //
    EXPORT_URI("export-uri.png"), //
    NEW("new.png"), //
    OPEN_REPO("open-repo.png"), //
    OPEN_URI("open-uri.png"), //
    OPEN_FILE("open-file.png"), //
    SAVE("save.png");

    private static final String PATH_PREFIX = "data/";

    private String name = null;

    private Icon(String name) {
        this.name = name;
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon(Icon.class.getResource(PATH_PREFIX + name));
    }
}
