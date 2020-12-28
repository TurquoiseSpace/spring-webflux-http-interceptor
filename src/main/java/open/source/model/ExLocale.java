package open.source.model;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExLocale extends ExBase implements Serializable {

	private String country;

	private String displayCountry;

	private String displayLanguage;

	private String displayName;

	private String displayScript;

	private String displayVariant;

	private Set<Character> extensionKeys;

	private String iso3Country;

	private String iso3Language;

	private String language;

	private String script;

	private Set<String> unicodeLocaleAttributes;

	private Set<String> unicodeLocaleKeys;

	private String variant;

	private boolean hasExtensions;

	private String toLanguageTag;

	private ExLocale stripExtensions;

	public ExLocale(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
