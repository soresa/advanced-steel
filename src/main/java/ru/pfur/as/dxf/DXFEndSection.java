package ru.pfur.as.dxf;

class DXFEndSection implements Builded {
    private static final String TYPE = "ENDSEC";

    public StringBuilder build() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(0).append(DXFConstant.NL).append(TYPE).append(DXFConstant.NL);
        return builder;
    }
}
