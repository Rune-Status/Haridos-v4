package com.runecore.cache;

import com.runecore.network.io.Message;
import com.runecore.network.io.MessageBuilder;
import com.runecore.util.BufferUtils;

/**
 * 
 * @author 'Mystic Flow <Steven@rune-server.org>
 */
public class CacheStore {

    public FileStore[] fileStores;
    public byte[] versionTable;
    public FileStore fs255;

    public CacheStore(String path, int cacheIndicies, boolean load) {
	fileStores = new FileStore[cacheIndicies];
	for (int i = 0; i < fileStores.length; i++) {
	    fileStores[i] = new FileStore(i, path);
	}
	if (load)
	    fs255 = new FileStore(255, path);
	if (CacheManager.versionTable != null)
	    versionTable = CacheManager.versionTable.clone();
    }

    public synchronized Message generateFile(int container, int file, int opcode) {
	byte[] cacheFile = getFile(container, file);
	int compression = cacheFile[0] & 0xFF;
	int length = BufferUtils.readInt(1, cacheFile);
	int attributes = compression;
	if (opcode == 0) {
	    attributes |= 0x80;
	}
	MessageBuilder outBuffer = new MessageBuilder();
	outBuffer.writeByte((byte) container);
	outBuffer.writeShort((short) file);
	outBuffer.writeByte((byte) attributes);
	outBuffer.writeInt(length);
	int realLength = compression != 0 ? length + 4 : length;
	for (int offset = 5; offset < realLength + 5; offset++) {
	    if (outBuffer.position() % 512 == 0) {
		outBuffer.writeByte((byte) 255);
	    }
	    outBuffer.writeByte(cacheFile[offset]);
	}
	return outBuffer.toMessage();
    }

    public byte[] getFile(int cache, int id) {
	if (cache == 255 && id == 255) {
	    return versionTable;
	}
	if (cache == 255) {
	    return fs255.get(id);
	}
	return fileStores[cache].get(id);
    }
}
