package com.geeeeezx.yimcp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class YiJing {
    private int id;
    private String symbol;           // 卦象符号，如 ䷀
    
    @JsonProperty("chinese_name")
    private String chineseName;      // 卦名，如"乾"
    
    @JsonProperty("pinyin_name")
    private String pinyinName;       // 拼音名，如"qian"
    
    @JsonProperty("upper_trigram")
    private String upperTrigram;     // 上卦
    
    @JsonProperty("lower_trigram")
    private String lowerTrigram;     // 下卦
    
    @JsonProperty("binary_sequence")
    private String binarySequence;   // 二进制序列，如"111111"
    
    private String description;      // 卦象描述
    
    @JsonProperty("yao_contents")
    private List<YaoContent> yaoContents;  // 爻辞内容
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class YaoContent {
        private String type;         // 类型（卦辞、爻辞、象传等）
        private String content;      // 具体内容
        private String position;     // 爻位（如：初九、六二等）
    }
} 