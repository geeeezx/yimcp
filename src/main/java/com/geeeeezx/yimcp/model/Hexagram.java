package com.geeeeezx.yimcp.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Hexagram {
    private List<Yao> yaoList;      // 六爻列表
    private String binarySequence;   // 二进制序列
    private YiJing originalGua;      // 本卦
    private YiJing changedGua;       // 变卦（如果有）
    
    public Hexagram() {
        this.yaoList = new ArrayList<>();
    }
    
    @Data
    public static class Yao {
        private int position;        // 爻位（1-6）
        private int value;          // 爻值（6,7,8,9）
        private boolean isChanging;  // 是否是变爻
        private String description;  // 爻的描述
        private String yinYang;     // "0" 阴 "1" 阳
        
        public Yao(int position, int value) {
            this.position = position;
            this.value = value;
            this.isChanging = (value == 6 || value == 9); // 6(老阴)和9(老阳)是变爻
            this.description = getYaoDescription(value);
            // 设置阴阳值
            this.yinYang = (value == 7 || value == 9) ? "1" : "0";
        }
        
        private String getYaoDescription(int value) {
            switch (value) {
                case 6: return "老阴 ○ →阳";  // 三阴，变阳
                case 7: return "少阳 ─";      // 二阳一阴
                case 8: return "少阴 --";     // 一阳二阴
                case 9: return "老阳 ⊕ →阴";  // 三阳，变阴
                default: return "无效爻值";
            }
        }
        
        public boolean isYang() {
            return value == 7 || value == 9;  // 7(少阳)和9(老阳)为阳爻
        }

//        public boolean isChanging() {
//            return this.isChanging;
//        }
    }
    
    public void addYao(int value) {
        if (value < 6 || value > 9) {
            throw new IllegalArgumentException("爻值必须在6-9之间");
        }
        Yao yao = new Yao(yaoList.size() + 1, value);
        yaoList.add(yao);
        updateBinarySequence();
    }
    
    private void updateBinarySequence() {
        StringBuilder binary = new StringBuilder();
        // 从下往上（第一爻到第六爻）组成卦象
        for (Yao yao : yaoList) {
            binary.append(yao.getYinYang());
        }
        this.binarySequence = binary.toString();
    }
    
    public boolean hasChangingLines() {
        return yaoList.stream().anyMatch(Yao::isChanging);
    }
    
    public String getChangedBinarySequence() {
        if (!hasChangingLines()) {
            return binarySequence;
        }
        
        StringBuilder changed = new StringBuilder();
        for (Yao yao : yaoList) {
            if (yao.isChanging()) {
                // 老阳(9)变阴(0)，老阴(6)变阳(1)
                changed.append(yao.getValue() == 9 ? "0" : "1");
            } else {
                changed.append(yao.getYinYang());
            }
        }
        return changed.toString();
    }
}