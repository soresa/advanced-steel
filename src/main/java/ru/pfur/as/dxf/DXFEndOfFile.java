package ru.pfur.as.dxf;

class DXFEndOfFile implements Builded {
    private static final String TYPE = "EOF";

    public StringBuilder build() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(0).append(DXFConstant.NL).append(TYPE).append(DXFConstant.NL);
        return builder;
    }
}
