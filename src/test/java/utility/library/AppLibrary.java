package utility.library;

public class AppLibrary {
    private final PageLibrary pageLibrary;
    private final FlowsLibrary flowsLibrary;

    public AppLibrary() {
        pageLibrary = new PageLibrary();
        flowsLibrary = new FlowsLibrary();
    }

    public PageLibrary getPageLibrary() {
        return pageLibrary;
    }

    public FlowsLibrary getFlowsLibrary() {
        return flowsLibrary;
    }
}
