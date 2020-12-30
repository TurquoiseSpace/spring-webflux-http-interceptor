package open.source.exchange.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExMultiValueMap;
import open.source.exchange.model.ExServerHttpRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class MultiValueMapParser {

	@Autowired
	private ObjectParser objectParser;

	public <K, V> ExMultiValueMap<K, V> parse(MultiValueMap<K, V> multiValueMap) {

		log.trace("parse -> (multiValueMap) {}", multiValueMap);
		ExMultiValueMap<K, V> exMultiValueMap = null;

		if (null != multiValueMap) {
			ExBase exBase = objectParser.parse(multiValueMap);
			exMultiValueMap = new ExMultiValueMap(exBase);

			int size = multiValueMap.size();
			exMultiValueMap.setSize(size);

			boolean isEmpty = multiValueMap.isEmpty();
			exMultiValueMap.setEmpty(isEmpty);

			if (size > 0) {
				Map<K, List<V>> mapVsList = new TreeMap<K, List<V>>();
				for (K key : multiValueMap.keySet()) {
					List<V> list = multiValueMap.get(key);
					List<V> values = new ArrayList<V>();
					values.addAll(list);
					mapVsList.put(key, values);
				}
				exMultiValueMap.setMapVsList(mapVsList);
			}
		}
		return exMultiValueMap;
	}

}
