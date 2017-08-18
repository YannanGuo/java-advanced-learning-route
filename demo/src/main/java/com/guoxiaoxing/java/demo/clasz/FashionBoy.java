package com.guoxiaoxing.java.demo.clasz;

/**
 * 时尚Boy
 * <p>
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/8/17 下午6:14
 */
public final class FashionBoy implements IDress {

    private IDress iDress;

    public FashionBoy(IDress iDress) {
        this.iDress = iDress;
    }

    @Override
    public void dressClothes() {
        iDress.dressClothes();
        DressLeather();
        DressJean();
    }

    public void DressLeather() {
        System.out.print("穿件皮衣");
    }

    public void DressJean() {
        System.out.print("穿件牛仔裤");
    }
}
