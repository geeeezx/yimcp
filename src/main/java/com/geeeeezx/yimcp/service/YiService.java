package com.geeeeezx.yimcp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geeeeezx.yimcp.model.Hexagram;
import com.geeeeezx.yimcp.model.YiJing;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class YiService {
    private final Random random = new Random();
    private List<YiJing> yiJingData;

    public YiService() {
        loadYiJingData();
    }

    private void loadYiJingData() {
        try {
            Resource resource = new ClassPathResource("static/yi-jing.json");
            if (!resource.exists()) {
                throw new RuntimeException("找不到易经数据文件：static/yi-jing.json");
            }
            ObjectMapper mapper = new ObjectMapper();
            yiJingData = mapper.readValue(resource.getInputStream(), 
                new TypeReference<List<YiJing>>() {});
            if (yiJingData == null || yiJingData.isEmpty()) {
                throw new RuntimeException("易经数据加载失败：数据为空");
            }
        } catch (IOException e) {
            throw new RuntimeException("加载易经数据时发生错误：" + e.getMessage(), e);
        }
    }

    @Tool(description = "六爻占卜（投掷铜钱法）")
    public String coinTossDivination(
            @ToolParam(description = "需要占卜的问题") String question) {
        StringBuilder result = new StringBuilder();
        result.append("问题：").append(question).append("\n\n");
        result.append("开始占卜...\n\n");

        // 创建新的卦象
        Hexagram hexagram = new Hexagram();

        // 从下往上投掷六次，每次三枚铜钱
        for (int i = 0; i < 6; i++) {
            int value = throwThreeCoins();
            hexagram.addYao(value);
            
            result.append(String.format("第%d爻：", i + 1));
            result.append(formatCoinResults(value));
            result.append("\n");
        }

        // 查找对应的卦象
        YiJing originalGua = findGuaBySequence(hexagram.getBinarySequence());
        hexagram.setOriginalGua(originalGua);

        // 生成结果解释
        result.append("\n━━━━━━━━━━ 卦象解读 ━━━━━━━━━━\n");
        result.append("本卦：").append(originalGua.getSymbol())
              .append(" ").append(originalGua.getChineseName())
              .append("卦 (").append(hexagram.getBinarySequence()).append(")\n");
        result.append("上卦：").append(originalGua.getUpperTrigram()).append("\n");
        result.append("下卦：").append(originalGua.getLowerTrigram()).append("\n");
        result.append("\n卦辞：").append(originalGua.getDescription()).append("\n\n");

        // 显示爻辞解释
        result.append("详细解读：\n");
        for (YiJing.YaoContent yao : originalGua.getYaoContents()) {
            if ("卦辞".equals(yao.getType())) {
                result.append("总体：").append(yao.getContent()).append("\n\n");
            } else if ("爻辞".equals(yao.getType())) {
                result.append(yao.getPosition()).append("：")
                      .append(yao.getContent()).append("\n");
            }
        }

        // 如果有变爻，显示变卦
        if (hexagram.hasChangingLines()) {
            String changedSequence = hexagram.getChangedBinarySequence();
            YiJing changedGua = findGuaBySequence(changedSequence);
            hexagram.setChangedGua(changedGua);
            
            result.append("\n━━━━━━━━━━ 变卦解读 ━━━━━━━━━━\n");
            result.append("变卦：").append(changedGua.getSymbol())
                  .append(" ").append(changedGua.getChineseName())
                  .append("卦 (").append(changedSequence).append(")\n");
            result.append("上卦：").append(changedGua.getUpperTrigram()).append("\n");
            result.append("下卦：").append(changedGua.getLowerTrigram()).append("\n");
            result.append("\n卦辞：").append(changedGua.getDescription()).append("\n\n");
            
            // 显示变卦爻辞
            result.append("变卦详解：\n");
            for (YiJing.YaoContent yao : changedGua.getYaoContents()) {
                if ("卦辞".equals(yao.getType())) {
                    result.append("总体：").append(yao.getContent()).append("\n\n");
                }
            }
        }

        return result.toString();
    }

    // 投掷三枚铜钱
    private int throwThreeCoins() {
        int yangCount = 0;
        // 投掷三次，计算正面（阳）的数量
        for (int i = 0; i < 3; i++) {
            if (random.nextBoolean()) {
                yangCount++;
            }
        }
        
        // 根据阳的数量返回对应的值
        switch (yangCount) {
            case 0: return 6;  // 老阴：没有阳（三阴）
            case 1: return 8;  // 少阴：一个阳
            case 2: return 7;  // 少阳：两个阳
            case 3: return 9;  // 老阳：三个阳
            default: throw new IllegalStateException("无效的投掷结果");
        }
    }

    // 格式化显示铜钱结果
    private String formatCoinResults(int value) {
        String yinyang;
        switch (value) {
            case 6: yinyang = "老阴 ○ →阳"; break;
            case 7: yinyang = "少阳 ─"; break;
            case 8: yinyang = "少阴 --"; break;
            case 9: yinyang = "老阳 ⊕ →阴"; break;
            default: yinyang = "无效";
        }
        return String.format("%d => %s", value, yinyang);
    }

    // 根据二进制序列查找卦象
    private YiJing findGuaBySequence(String sequence) {
        return yiJingData.stream()
            .filter(gua -> gua.getBinarySequence().equals(sequence))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("未找到对应的卦象：" + sequence));
    }
}
