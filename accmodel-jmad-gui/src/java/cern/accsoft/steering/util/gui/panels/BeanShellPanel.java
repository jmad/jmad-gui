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

package cern.accsoft.steering.util.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cern.accsoft.steering.jmad.util.ListUtil;
import cern.accsoft.steering.jmad.util.ListUtil.Mapper;
import cern.accsoft.steering.util.gui.script.ScriptConsole;

/**
 * This Class encapsulates the creation of the panel for the bean-shell
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class BeanShellPanel extends JPanel implements ApplicationContextAware {
    private static final long serialVersionUID = 4374231778801633419L;

    /**
     * the name of the variable under which the application context will be accessible in BeanShell
     */
    private final static String BSH_APPCONTEXT_NAME = "ctx";

    /** The manager which detects all the available engines. */
    private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

    /** The interactive console */
    private ScriptConsole console = new ScriptConsole();

    /**
     * init-method called by spring
     */
    public void init() {
        initComponents();
    }

    /**
     * creates all contained components
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        List<ScriptEngineFactoryItem> items = ListUtil.map(this.scriptEngineManager.getEngineFactories(),
                new Mapper<ScriptEngineFactory, ScriptEngineFactoryItem>() {
                    @Override
                    public ScriptEngineFactoryItem map(ScriptEngineFactory inobject) {
                        return new ScriptEngineFactoryItem(inobject);
                    }
                });

        final JComboBox cboScriptingEngineFactories = new JComboBox(items.toArray());

        cboScriptingEngineFactories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = cboScriptingEngineFactories.getSelectedItem();
                if (item == null) {
                    return;
                }
                ScriptEngineFactory factory = ((ScriptEngineFactoryItem) item).getFactory();
                setScriptEngine(factory);
            }
        });
        if (items.size() > 0) {
            setScriptEngine(items.get(0).getFactory());
        }
        add(cboScriptingEngineFactories, BorderLayout.NORTH);

        add(this.console, BorderLayout.CENTER);

    }

    private void setScriptEngine(ScriptEngineFactory factory) {
        /*
         * we let the manager do the creation of the engine. By that method the bindings should be set correctly.
         */
        String shortName = factory.getNames().get(0);
        ScriptEngine engine = scriptEngineManager.getEngineByName(shortName);
        this.console.setScriptEngine(engine);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.scriptEngineManager.put(BSH_APPCONTEXT_NAME, applicationContext);
    }

    /**
     * @param beanShellObjects the beanShellObjects to set
     */
    public void setContextObjects(Map<String, Object> contextObjects) {
        scriptEngineManager.getBindings().putAll(contextObjects);
    }

    /**
     * just a wrapper for ScriptEngineFactories whose toString Method returns a nice name;
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private static class ScriptEngineFactoryItem {

        /** the wrapped {@link ScriptEngineFactory} */
        private ScriptEngineFactory factory;

        private ScriptEngineFactoryItem(ScriptEngineFactory factory) {
            this.factory = factory;
        }

        public String toString() {
            return this.factory.getEngineName();
        }

        public ScriptEngineFactory getFactory() {
            return this.factory;
        }
    }

}
