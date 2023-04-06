package lt.codeacademy.learn.baigiamasis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaigiamasisDarbasApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void citShouldAddNumbers() {
		int numberOne = 20;
		int numberTwo = 30;


	}


	static class Calculator{
		int add(int a , int b){
			return a + b;
		}
	}
}
