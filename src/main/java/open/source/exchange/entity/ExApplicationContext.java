package open.source.exchange.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEnvironment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(value = "applicationcontext")
public class ExApplicationContext extends ExBase implements Serializable {

	@Id
	// TODO : environment.activeProfiles[0] + startupDate
	private String documentId;

	private String applicationName;

	private int beanDefinitionCount;

	private String[] beanDefinitionNames;

	private String displayName;

	private ExEnvironment environment;

	private String id;

	@Indexed(background = true)
	private long startupDate;

	private ExApplicationContext parent;

	public ExApplicationContext(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
