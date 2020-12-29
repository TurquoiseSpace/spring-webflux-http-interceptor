package open.source.exchange.parser;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExCharset;
import open.source.exchange.model.ExMediaType;

@Log4j2
@Service
@RequiredArgsConstructor
public class MediaTypeParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private CharsetParser charsetParser;

	private ExMediaType parseCore(MediaType mediaType) {

		log.info("parse core -> (mediaType) {}", mediaType);
		ExMediaType exMediaType = null;

		if (null != mediaType) {
			ExBase exBase = objectParser.parse(mediaType);
			exMediaType = new ExMediaType(exBase);

			Charset charset = mediaType.getCharset();
			ExCharset exCharset = charsetParser.parse(charset);
			exMediaType.setCharset(exCharset);

			Map<String, String> parameters = mediaType.getParameters();
			exMediaType.setParameters(parameters);

			double qualityValue = mediaType.getQualityValue();
			exMediaType.setQualityValue(qualityValue);

			String type = mediaType.getType();
			exMediaType.setType(type);

			String subtype = mediaType.getSubtype();
			exMediaType.setSubtype(subtype);

			boolean isConcrete = mediaType.isConcrete();
			exMediaType.setConcrete(isConcrete);

			boolean isWildcardType = mediaType.isWildcardType();
			exMediaType.setWildcardType(isWildcardType);

			boolean isWildcardSubtype = mediaType.isWildcardSubtype();
			exMediaType.setWildcardSubtype(isWildcardSubtype);
		}
		return exMediaType;
	}

	public ExMediaType parse(MediaType mediaType) {

		log.info("parse -> (mediaType) {}", mediaType);
		ExMediaType exMediaType = parseCore(mediaType);

		if (null != exMediaType) {
			MediaType removeQualityValue = mediaType.removeQualityValue();
			ExMediaType exRemoveQualityValue = parseCore(removeQualityValue);
			exMediaType.setRemoveQualityValue(exRemoveQualityValue);
		}
		return exMediaType;
	}

}
