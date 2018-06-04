import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<K> implements CountMap<K> {

    private Map<K, Integer> map = new HashMap<>();

    @Override
    public void add(K o) {
        System.out.println("Добавляем элемент " + o);
        Integer val = map.putIfAbsent(o, 1);
        if (val != null) {
            map.replace(o, ++val);
        }
    }

    @Override
    public int getCount(K o) {
        int count = 0;
        if (map.containsKey(o)) {
            count = map.get(o);
        }
        System.out.println("Количество элементов " + o + ": " + count);
        return count;
    }

    @Override
    public int remove(K o) {
        System.out.println("Удаляем элемент " + o);
        if (map.containsKey(o)) {
            Integer val = map.get(o);
            if (val > 1) {
                return map.replace(o, --val);
            } else {
                return map.remove(o);
            }
        } else {
            return 0;
        }
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<K> source) {
        for (K el : source.toMap().keySet()) {
            Integer srcVal = source.getCount(el);
            if (!map.containsKey(el)) {
                map.put(el, srcVal);
            } else {
                map.replace(el, map.get(el) + srcVal);
            }
        }
    }

    @Override
    public Map<K, Integer> toMap() {
        return map;
    }

    @Override
    public void toMap(Map<K, Integer> destination) {
        if (!this.map.equals(destination)) {
            destination.clear();
            destination.putAll(map);
        }
    }
}