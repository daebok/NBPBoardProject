package com.hyunhye.board.service;

import org.junit.Test;

public class FilteringTest {

	@Test
	public void test() {

		// ===============================================
		String content = "첫째로, static은 객체 지향적이지 않습니다:  개발자들이 static 변수를 ‘악’으로 규정하는 이유는 static 변수가 객체 지향의 패러다임과 상반되기 때문입니다. 특히나 static 변수는, 각 객체의 데이터들이 캡슐화되어야 한다는 객체지향 프로그래밍의 원칙(역주: 한 객체가 가지고 있는 데이터들은 외부에서 함부로 접근하여 수정할 수 없도록 해야 한다는 원칙)에 위반됩니다. 질문자께서 스스로 설명했듯이 static은 스코프(역주: 한 변수가 유효한 범위)를 고려할 필요가 없는 경우, 즉 전역 변수를 사용할 때에 유용합니다. 이는 절차지향적 프로그래밍 혹은 명령형 프로그래밍(역주: C가 대표적인 절차지향적, 명령형 프로그래밍 언어이며 Java 역시 큰 범위에서 절차지향적, 명령형 프로그래밍 언어라고 할 수 있다.)에서 매우 중요한 개념입니다. 하지만 이 것이 객체지향의 관점에서 좋은 코드라고 얘기하기는 힘듭니다. 절차지향 패러다임이 나쁘다는 것이 아닙니다. 다만, 당신의 시니어는 당신이 “객체지향적으로 좋은 코드”를 짜기를 바라는 것입니다. 반대로 당신은 “절차지향적으로 좋은 코드”를 짜기를 원하는 것이라고 말할 수 있을 것입니다.;";
		content += content;
		content += content;
		content += "시발";
		content += content;
		content += content;
		System.out.println(content.length());

		long startTime = System.currentTimeMillis();
		System.out.println(Filtering.badWordFiltering(content));
		long endTime = System.currentTimeMillis();

		// Total time
		long lTime = endTime - startTime;
		System.out.println("TIME : " + lTime * 0.001 + "(s)");

	}

}
