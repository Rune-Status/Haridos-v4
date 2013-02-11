package com.runecore.cache;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

import com.runecore.cache.stream.RSByteArrayInputStream;
import com.runecore.cache.stream.RSByteBuffer;
import com.runecore.network.io.Message;
import com.runecore.network.io.MessageBuilder;
import com.runecore.util.BufferUtils;

public class CacheManager {

    private static FileInformationTable[] informationTables;
    public static CacheStore cacheStore;
    private static FileStore fs255;
    private static FileStore[] fileStores;
    private static Object[][][] archiveFiles;
    public static byte[] versionTable;
    public static int cacheIndicies;

    public static void load(String path) throws Exception {
	FileStore fs255 = new FileStore(255, path);

	cacheIndicies = fs255.length();
	cacheStore = new CacheStore(path, cacheIndicies, false);
	fileStores = cacheStore.fileStores;
	informationTables = new FileInformationTable[cacheIndicies + 1];
	archiveFiles = new Object[cacheIndicies][][];
	for (int i = 0; i < informationTables.length; i++) {
	    byte[] data = fs255.get(i);
	    if (data != null) {
		informationTables[i] = FileInformationTable
			.createFileInformationTable(i, data);
		archiveFiles[i] = new Object[informationTables[i]
			.getEntryCount()][];
	    }
	}
	int length = cacheIndicies;
	int size = length * 74 + 3;
	CRC32 crc = new CRC32();
	ByteBuffer mainFileBuffer = ByteBuffer.allocate(size);
	mainFileBuffer.put((byte) 0).putInt(size - 5).put((byte) length);
	for (int i = 0; i < length; i++) {
	    byte[] file = fs255.get(i);
	    if (file == null) {
		mainFileBuffer.putInt(0).putInt(0)
			.put(Whirlpool.whirlpool(new byte[0], 0, 0));
		continue;
	    }
	    crc.update(file);
	    mainFileBuffer.putInt((int) crc.getValue())
		    .putInt(informationTables[i].getRevision())
		    .put(Whirlpool.whirlpool(file, 0, file.length));
	    crc.reset();
	}
	int bufferPosition = mainFileBuffer.position();
	mainFileBuffer.rewind();
	byte[] mainFileData = new byte[bufferPosition];
	mainFileBuffer.get(mainFileData).rewind().position(bufferPosition);
	mainFileBuffer.put((byte) 10).put(
		Whirlpool.whirlpool(mainFileData, 5, mainFileData.length - 5)); // TODO
										// Fix
										// this

	versionTable = mainFileBuffer.array();
    }

    public static FileStore getCrc() {
	return fs255;
    }

    public static FileInformationTable getFIT(int cache) {
	return informationTables[cache];
    }

    public static byte[] getByName(int cache, String name) {
	int id = informationTables[cache].findName(name);
	return fileStores[cache].get(id);
    }

    public static byte[] getFile(int cache, int id) {
	if (cache == 255 && id == 255) {
	    return versionTable;
	}
	if (cache == 255) {
	    return fs255.get(id);
	}
	return fileStores[cache].get(id);
    }

    public static FileStore getFileStore(int cache) {
	if (cache == 255) {
	    return fs255;
	}
	return fileStores[cache];
    }

    public static boolean loadArchive(int cache, int main) throws IOException {
	if (archiveFiles[cache].length < main) {
	    main = archiveFiles[cache].length;
	}
	int[] is_11_ = informationTables[cache].getEntry_sub_ptr()[main];
	boolean bool = true;
	int count = informationTables[cache].getArchiveCount()[main];
	if (archiveFiles[cache][main] == null)
	    archiveFiles[cache][main] = new Object[count];
	Object[] objects = archiveFiles[cache][main];
	for (int i_13_ = 0; i_13_ < count; i_13_++) {
	    int i_14_;
	    if (is_11_ != null)
		i_14_ = is_11_[i_13_];
	    else
		i_14_ = i_13_;
	    if (objects[i_14_] == null) {
		bool = false;
		break;
	    }
	}
	if (bool) {
	    return true;
	}
	byte[] is_16_ = fileStores[cache].get(main);
	byte[] data = null;
	try {
	    data = new CacheContainer(is_16_).decompress();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (data == null)
	    return false;
	if (count > 1) {
	    int length = data.length;
	    int blockLength = data[--length] & 0xff;
	    RSByteBuffer stream = new RSByteBuffer(new RSByteArrayInputStream(
		    data));
	    length -= blockLength * (count * 4);
	    stream.seek(length);
	    int[] dataOffset = new int[count];
	    for (int x = 0; x < blockLength; x++) {
		int offset = 0;
		for (int i = 0; i < count; i++) {
		    offset += stream.readInt();
		    dataOffset[i] += offset;
		}
	    }
	    byte[][] subData = new byte[count][];
	    for (int i = 0; i < count; i++) {
		subData[i] = new byte[dataOffset[i]];
		dataOffset[i] = 0;
	    }
	    int readPos = 0;
	    stream.seek(length);
	    for (int i = 0; i < blockLength; i++) {
		int position = 0;
		for (int subId = 0; subId < count; subId++) {
		    position += stream.readInt();
		    System.arraycopy(data, readPos, subData[subId],
			    dataOffset[subId], position);
		    readPos += position;
		    dataOffset[subId] += position;
		}
	    }
	    for (int i = 0; i < count; i++) {
		objects[is_11_ != null ? is_11_[i] : i] = subData[i];
	    }
	    stream.close();
	} else {
	    int i_32_;
	    if (is_11_ != null)
		i_32_ = is_11_[0];
	    else
		i_32_ = 0;
	    objects[i_32_] = data;
	}
	archiveFiles[cache][main] = objects;
	return true;
    }

    public static Message generateFile(int container, int file, int opcode) {
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

    public static byte[] getData(int cache, int main, int child)
	    throws IOException {
	if (!loadArchive(cache, main))
	    throw new IOException("Data not available");
	if (archiveFiles[cache].length < main) {
	    main = archiveFiles[cache].length;
	}
	return (byte[]) archiveFiles[cache][main][child];
    }

    public static int containerCount(int cache) {
	return archiveFiles[cache].length;
    }

    public static int cacheCFCount(int cache) {
	int lastcontainer = containerCount(cache) - 1;
	return 256 * lastcontainer
		+ getRealContainerChildCount(cache, lastcontainer);
    }

    public static int cacheCFCount2(int cache) {
	int lastcontainer = containerCount(cache) - 1;
	return 128 * lastcontainer
		+ getRealContainerChildCount(cache, lastcontainer);
    }

    public static int getRealContainerChildCount(int cache, int lastcontainer) {
	return informationTables[cache].getEntry_real_sub_count()[lastcontainer];
    }

    public static int getContainerChildCount(int cache, int lastcontainer) {
	return informationTables[cache].getArchiveCount()[lastcontainer];
    }

    public static int getChildCount(int cache, int main) throws IOException {
	if (!loadArchive(cache, main))
	    throw new IOException("Data not available");
	if (archiveFiles[cache].length < main) {
	    main = archiveFiles[cache].length;
	}
	return archiveFiles[cache][main].length;
    }

}