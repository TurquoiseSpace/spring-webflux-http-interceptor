package open.source.exchange.parser;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExInetAddress;
import open.source.exchange.model.ExInetSocketAddress;

@Log4j2
@Service
@RequiredArgsConstructor
public class InetAddressParser {

	@Autowired
	private ObjectParser objectParser;

	public ExInetAddress parse(InetAddress inetAddress) {

		log.debug("parse -> (inetAddress) {}", inetAddress);
		ExInetAddress exInetAddress = null;

		if (null != inetAddress) {
			ExBase exBase = objectParser.parse(inetAddress);
			exInetAddress = new ExInetAddress(exBase);

			byte[] address = inetAddress.getAddress();
			exInetAddress.setAddress(address);

			String canonicalHostName = inetAddress.getCanonicalHostName();
			exInetAddress.setCanonicalHostName(canonicalHostName);

			String hostAddress = inetAddress.getHostAddress();
			exInetAddress.setHostAddress(hostAddress);

			String hostName = inetAddress.getHostName();
			exInetAddress.setHostName(hostName);

			boolean anyLocalAddressFlag = inetAddress.isAnyLocalAddress();
			exInetAddress.setAnyLocalAddressFlag(anyLocalAddressFlag);

			boolean linkLocalAddressFlag = inetAddress.isLinkLocalAddress();
			exInetAddress.setLinkLocalAddressFlag(linkLocalAddressFlag);

			boolean loopbackAddressFlag = inetAddress.isLoopbackAddress();
			exInetAddress.setLoopbackAddressFlag(loopbackAddressFlag);

			boolean multicastGlobalFlag = inetAddress.isMCGlobal();
			exInetAddress.setMulticastGlobalFlag(multicastGlobalFlag);

			boolean multicastLinkLocalFlag = inetAddress.isMCLinkLocal();
			exInetAddress.setMulticastLinkLocalFlag(multicastLinkLocalFlag);

			boolean multicastNodeLocalFlag = inetAddress.isMCNodeLocal();
			exInetAddress.setMulticastNodeLocalFlag(multicastNodeLocalFlag);

			boolean multicastOrgLocalFlag = inetAddress.isMCOrgLocal();
			exInetAddress.setMulticastOrgLocalFlag(multicastOrgLocalFlag);

			boolean multicastSiteLocalFlag = inetAddress.isMCSiteLocal();
			exInetAddress.setMulticastSiteLocalFlag(multicastSiteLocalFlag);

			boolean multicastAddressFlag = inetAddress.isMulticastAddress();
			exInetAddress.setMulticastAddressFlag(multicastAddressFlag);

			boolean siteLocalAddressFlag = inetAddress.isSiteLocalAddress();
			exInetAddress.setSiteLocalAddressFlag(siteLocalAddressFlag);
		}
		return exInetAddress;
	}

}
