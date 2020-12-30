package open.source.exchange.parser;

import java.net.InetAddress;
import java.net.InetSocketAddress;

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
public class InetSocketAddressParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private InetAddressParser inetAddressParser;

	public ExInetSocketAddress parse(InetSocketAddress inetSocketAddress) {

		log.debug("parse -> (inetSocketAddress) {}", inetSocketAddress);
		ExInetSocketAddress exInetSocketAddress = null;

		if (null != inetSocketAddress) {
			ExBase exBase = objectParser.parse(inetSocketAddress);
			exInetSocketAddress = new ExInetSocketAddress(exBase);

			InetAddress address = inetSocketAddress.getAddress();
			ExInetAddress exInetAddress = inetAddressParser.parse(address);
			exInetSocketAddress.setAddress(exInetAddress);

			String hostName = inetSocketAddress.getHostName();
			exInetSocketAddress.setHostName(hostName);

			String hostString = inetSocketAddress.getHostString();
			exInetSocketAddress.setHostString(hostString);

			int port = inetSocketAddress.getPort();
			exInetSocketAddress.setPort(port);

			boolean unresolvedFlag = inetSocketAddress.isUnresolved();
			exInetSocketAddress.setUnresolvedFlag(unresolvedFlag);
		}
		return exInetSocketAddress;
	}

}
