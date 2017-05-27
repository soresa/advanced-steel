package ru.pfur.as.dxf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DXFLine implements Builded {
    private static final String X_START = "10";
    private static final String X_END = "11";
    private static final String Y_START = "20";
    private static final String Y_END = "21";
    private final String TYPE = "LINE";
    private double xStart;
    private double yStart;
    private double xEnd;
    private double yEnd;

    public StringBuilder build() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(0).append(DXFConstant.NL).append(TYPE).append(DXFConstant.NL)
                .append(X_START).append(DXFConstant.NL).append(xStart).append(DXFConstant.NL)
                .append(Y_START).append(DXFConstant.NL).append(yStart).append(DXFConstant.NL)
                .append(X_END).append(DXFConstant.NL).append(xEnd).append(DXFConstant.NL)
                .append(Y_END).append(DXFConstant.NL).append(yEnd).append(DXFConstant.NL);
        return builder;
    }

}
