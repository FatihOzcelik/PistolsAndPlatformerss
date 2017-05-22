package dk.sdu.pistolsandplatformerstest;

import dk.sdu.common.services.IPluginService;
import dk.sdu.common.services.IProcessingService;
import java.io.IOException;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;

/**
 *
 * @author fatihozcelik
 * 
 * Integration test
 * Source: https://drive.google.com/file/d/0B6Mo6Uok0on9d3ZTV0tHbXk3RWs/edit
 * 
 * 
 * IMPORTANT!:
 * Before running test, remember to:
 *  1. Remove/uncomment all entity-modules from UPDATE_FILE
 *  2. Check that ADD_ENEMY_UPDATES_FILE contains NON-commented enemy module
 *  3. Check that REMOVE_ENEMY_UPDATES_FILE contains OUT-commented enemy module
 * Eventually clean and build after step 1, so that all installed modules gets uninstalled!
 * 
 */
public class IntegrationTest extends NbTestCase {

    private static final String ADD_ENEMY_UPDATES_FILE = "/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/application/src/test/resources/netbeans_site-enemy-added/updates.xml";
    private static final String REMOVE_ENEMY_UPDATES_FILE = "/Users/fatihozcelik/NetBeansProjects/PistolsAndPlatformerss/application/src/test/resources/netbeans_site-enemy-removed/updates.xml";
    private static final String UPDATES_FILE = "/Users/fatihozcelik/Desktop/netbeans_site_pistols_and_platformers/updates.xml";

    // When we do this, then we get a startup of the whole module system, when
    // we run the test
    public static Test suite() {
        return NbModuleSuite.createConfiguration(IntegrationTest.class).
                gui(false).
                failOnMessage(Level.WARNING).
                failOnException(Level.INFO).
                enableClasspathModules(false).
                clusters(".*").
                suite();
    }

    public IntegrationTest(String name) {
        super(name);
    }

    public void testApplication() throws InterruptedException, IOException {
        //Setup
        List<IProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IPluginService> plugins = new CopyOnWriteArrayList<>();
        waitForUpdate(processors, plugins);

        //Pre asserts
        //Size should be 0 because no modules are installed
        assertEquals("No plugins", 0, plugins.size());
        assertEquals("No processors", 0, processors.size());

        // Test: load enemy with update center
        // Gets the update files with enemy and replaces it with the update-file,
        // which doesnt have the enemy.
        // This is the same as we >>outcomment<< the enemy in the update center
        copy(get(ADD_ENEMY_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);

        // Asserts: Enemy loaded
        assertEquals("One plugins", 1, plugins.size());
        assertEquals("One processors", 1, processors.size());

        // Test: Unload enemy with update center
        copy(get(REMOVE_ENEMY_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);

        // Asserts: Enemy unloaded
        assertEquals("No plugins", 0, plugins.size());
        assertEquals("No processors", 0, processors.size());

    }

    private void waitForUpdate(List<IProcessingService> processors, List<IPluginService> plugins) throws InterruptedException {
        // Needs time for silentUpdater to install all modules
        // it takes some time for the silentupdater before it has been updated
        Thread.sleep(10000);
        processors.clear();
        processors.addAll(Lookup.getDefault().lookupAll(IProcessingService.class));

        plugins.clear();
        plugins.addAll(Lookup.getDefault().lookupAll(IPluginService.class));

    }

}
