package im.actor.sdk.core.controllers.messages;

public class PreprocessedList {

    private final PreprocessedData[] preprocessedData;

    public PreprocessedList(PreprocessedData[] preprocessedData) {
        this.preprocessedData = preprocessedData;
    }

    public PreprocessedData[] getPreprocessedData() {
        return preprocessedData;
    }
}
