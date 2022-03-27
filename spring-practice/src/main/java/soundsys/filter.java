package soundsys;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class filter {
    public static <T> List<T> fil(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result; };
    public static void main(String[] args) {
        List<Integer> test = fil(new ArrayList<>(), (Integer i)->{ return i>2 ? true: false ;});
    }
}
interface Predicate<T>{
    boolean test(T t);
}

