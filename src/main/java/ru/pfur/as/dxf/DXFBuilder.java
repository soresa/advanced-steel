package ru.pfur.as.dxf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DXFBuilder {
    private List<Builded> entities = new ArrayList<Builded>();
    private boolean builded = false;

    public DXFBuilder() throws DXFBuilderException {
        this
                .add(new DXFSections())
                .add(new DXFEntities());
    }

    public DXFBuilder add(Builded builded) throws DXFBuilderException {
        if (!this.isBuilded()) {
            entities.add(builded);
            return this;
        }
        throw new DXFBuilderException("Object already build, pls recreate this");

    }

    public void build(File dxfFile) throws DXFBuilderException, IOException {
        beforeBuildFile();
        FileWriter writer = null;
        try {
            writer = new FileWriter(dxfFile, false);
            for (Builded builded : entities) {
                writer.append(builded.build().toString());
            }
        } finally {
            if (writer != null) writer.close();
            builded = true;
        }
    }

    public boolean isBuilded() {
        return builded;
    }

    private void beforeBuildFile() throws DXFBuilderException {
        this
                .add(new DXFEndSection())
                .add(new DXFEndOfFile());
    }
}
