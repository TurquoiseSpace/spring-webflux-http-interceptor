package open.source.parser;

import java.nio.charset.Charset;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExBase;
import open.source.model.ExCharset;
import open.source.model.ExContentDisposition;
import open.source.model.ExZonedDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class ContentDispositionParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private CharsetParser charsetParser;

	@Autowired
	private ZonedDateTimeParser zonedDateTimeParser;

	public ExContentDisposition parse(ContentDisposition contentDisposition) {

		log.info("parse -> (contentDisposition) {}", contentDisposition);
		ExContentDisposition exContentDisposition = null;

		if (null != contentDisposition) {
			ExBase exBase = objectParser.parse(contentDisposition);
			exContentDisposition = new ExContentDisposition(exBase);

			Charset charset = contentDisposition.getCharset();
			ExCharset exCharset = charsetParser.parse(charset);
			exContentDisposition.setCharset(exCharset);

			ZonedDateTime creationDate = contentDisposition.getCreationDate();
			ExZonedDateTime exCreationDate = zonedDateTimeParser.parse(creationDate);
			exContentDisposition.setCreationDate(exCreationDate);

			String filename = contentDisposition.getFilename();
			exContentDisposition.setFilename(filename);

			ZonedDateTime modificationDate = contentDisposition.getModificationDate();
			ExZonedDateTime exModificationDate = zonedDateTimeParser.parse(modificationDate);
			exContentDisposition.setModificationDate(exModificationDate);

			String name = contentDisposition.getName();
			exContentDisposition.setName(name);

			ZonedDateTime readDate = contentDisposition.getReadDate();
			ExZonedDateTime exReadDate = zonedDateTimeParser.parse(readDate);
			exContentDisposition.setReadDate(exReadDate);

			Long size = contentDisposition.getSize();
			exContentDisposition.setSize(size);

			String type = contentDisposition.getType();
			exContentDisposition.setType(type);
		}
		return exContentDisposition;
	}

}
