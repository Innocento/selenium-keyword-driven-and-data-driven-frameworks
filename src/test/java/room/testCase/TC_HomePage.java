package room.testCase;

import org.testng.annotations.Test;
import room.config.StartBrowser;
import room.reuse.CommonFunctions;
//import room.utils.MyScreenRecorder;

public class TC_HomePage extends StartBrowser {
    @Test
    public void testHomePage() throws Exception {
        CommonFunctions cfs = new CommonFunctions();
        cfs.homePage();

        //MyScreenRecorder.stopRecording();
    }
}
