/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.autoupdate.silentupdate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.openide.modules.ModuleInstall;

/**
 *
 * @author fatihozcelik
 */
public class UpdateActivator extends ModuleInstall {

    private final ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);

    @Override
    public void restored() {
        exector.scheduleAtFixedRate(doCheck, 5000, 5000, TimeUnit.MILLISECONDS);
    }

    private static final Runnable doCheck = new Runnable() {
        @Override
        public void run() {
            if (UpdateHandler.timeToCheck()) {
                UpdateHandler.checkAndHandleUpdates();
            }
        }

    };

    @Override
    public void uninstalled() {
        super.uninstalled(); //To change body of generated methods, choose Tools | Templates.
    }

}
