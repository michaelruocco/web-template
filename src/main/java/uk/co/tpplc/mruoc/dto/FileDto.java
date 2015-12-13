package uk.co.tpplc.mruoc.dto;

public class FileDto {

    private String filename;
    private String contents;

    public String getName() {
        return filename;
    }

    public void setName(String filename) {
        this.filename = filename;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
