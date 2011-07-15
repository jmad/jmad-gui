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

package cern.accsoft.steering.jmad.gui.manage;

import java.io.File;
import java.util.List;

/**
 * This is the interface of a class which keeps track of custom files, that can be added and executed.
 * 
 * @author kfuchsbe
 */
public interface CustomFileManager {

    /**
     * adds a file to the manager
     * 
     * @param file the file to add
     */
    public void add(File file);

    /**
     * removes a file from the manager.
     * 
     * @param file the file to remove
     */
    public void remove(File file);

    /**
     * @return all the currently available files
     */
    public List<File> getFiles();

    /**
     * adds a listener to the {@link CustomFileManager}
     * 
     * @param listener the listener to add
     */
    public void addListener(CustomFileManagerListener listener);

    /**
     * removes a listener from the {@link CustomFileManager}
     * 
     * @param listener the listener to remove
     */
    public void removeListener(CustomFileManagerListener listener);

}
