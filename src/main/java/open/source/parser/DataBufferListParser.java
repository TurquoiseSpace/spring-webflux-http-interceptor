package open.source.parser;

import java.util.List;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class DataBufferListParser {

	public String parse(List<DataBuffer> dataBufferList) {

		StringBuilder stringBuilder = new StringBuilder();
		if (null != dataBufferList) {
			try {
				for (DataBuffer dataBuffer : dataBufferList) {
					log.info("data buffer -> (capacity) {} (readableByteCount) {}", dataBuffer.capacity(), dataBuffer.readableByteCount());
					int size = dataBuffer.readableByteCount() < dataBuffer.capacity()
						? dataBuffer.readableByteCount()
						: dataBuffer.capacity();
					byte[] byteArray = new byte[size];
					for (int index = 0; index < size; index++) {
						byte current = dataBuffer.getByte(index);
						byteArray[index] = current;
					}
					log.info("formed -> (byteArray) {}", byteArray);
					String dataBufferString = new String(byteArray);
					log.info("extracted -> (dataBufferString) {}", dataBufferString);
					stringBuilder.append(dataBufferString);
				}
			} catch (UnsupportedOperationException e) {
				log.error("error identifying String from dataBufferList", e);
			}
		}
		String completeDataBufferString = stringBuilder.toString();
		log.info("got -> (completeDataBufferString) {}", completeDataBufferString);
		return completeDataBufferString;
	}

}
