package ru.pfur.as.dxf;

class DXFEntities implements Builded {
    private static final String TYPE = "ENTITIES";

    public StringBuilder build() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(2).append(DXFConstant.NL).append(TYPE).append(DXFConstant.NL);
        return builder;
    }
}
