//구현 테스트를 위한 테스트 데이터 로드해주는 코드
//package com.test.demo.service;
//import com.test.demo.entity.Recipe;
//import com.test.demo.repository.RecipeRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.IntStream;
//
//@Component
//public class RecipeDataLoader {
//
//    @Bean
//    CommandLineRunner init(RecipeRepository recipeRepository) {
//        return args -> {
//            Random random = new Random();
//            String[] title = {"김치볶음밥", "비빔밥", "파스타", "초밥", "된장찌개", "떡볶이", "짜장면", "피자", "마파두부"};
//            String[] categories = {"한식", "중식", "양식", "일식"};
//            String[] ingredients = {"소고기", "돼지고기", "닭고기", "감자", "당근", "마늘", "양파", "고추"};
//
//            IntStream.range(0, 9).forEach(i -> {
//                String recipeTitle = title[i];
//
//                List<String> ingredientList = new ArrayList<>(Arrays.asList(ingredients));
//                Collections.shuffle(ingredientList, random);
//                List<String> selectedIngredients = ingredientList.subList(0, 5);
//                String ingredientString = String.join(", ", selectedIngredients);
//
//                String category = categories[random.nextInt(categories.length)];
//
//                recipeRepository.save(Recipe.builder()
//                        .title(recipeTitle)
//                        .ingredients(ingredientString)
//                        .category(category)
//                        .date(LocalDateTime.now())
//                        .build());
//            });
//        };
//    }
//}
//
