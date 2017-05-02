package com.guoxiaoxing.java.demo.Annotation;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/5/2 上午9:35
 */
public class Fruit {

    @FruitName(fruitName = "apple")
    private String fruitName;

    @FruitColor(fruitColor = FruitColor.Color.GREEN)
    private String fruitColor;

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitColor() {
        return fruitColor;
    }

    public void setFruitColor(String fruitColor) {
        this.fruitColor = fruitColor;
    }
}
