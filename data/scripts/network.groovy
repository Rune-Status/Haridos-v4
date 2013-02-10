class Network implements GroovyScript {

    void init(Context context) {
	def socket = new InetSocketAddress("0.0.0.0", 43594);
	def network = new NetworkContext(socket);
	network.configure(context);
	network.bind();
    }
}