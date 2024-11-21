package chapter03;

public class GoodsApp {

	public static void main(String[] args) {
		Goods goods = new Goods();
		
		goods.setName("nikon");
		goods.setPrice(400000);
		goods.setCountSold(10);
		goods.setCountStock(20);
		
//		System.out.println("상품이름: " + goods.getName() + 
//				", 가격: " + goods.getPrice() + 
//				", 재고개수: " + goods.getCountStock() + 
//				", 판매량" + goods.getCountSold());
		
		goods.printInfo();
		
		// 정보 은닉 (객체의 상태를 보호)
		goods.setPrice(-1000);
		
		Goods goods2 = new Goods();
		Goods goods3 = new Goods();
		// static 변수 (클래스 변수)
		System.out.println(Goods.countOfGoods);
		
		goods.setPrice(40000);
		System.out.println(goods.calcDiscountPrice(0.5f));
	}

}
