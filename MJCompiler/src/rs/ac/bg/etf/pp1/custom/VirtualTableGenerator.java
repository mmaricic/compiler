package rs.ac.bg.etf.pp1.custom;

import java.util.ArrayList;

import rs.etf.pp1.mj.runtime.Code;

public class VirtualTableGenerator {

	ArrayList<Byte> MethodTable = new ArrayList<Byte>();

	void addWordToStaticData(int value, int address) {
		MethodTable.add(new Byte((byte) Code.const_));
		MethodTable.add(new Byte((byte) ((value >> 16) >> 8)));
		MethodTable.add(new Byte((byte) (value >> 16)));
		MethodTable.add(new Byte((byte) (value >> 8)));
		MethodTable.add(new Byte((byte) value));
		MethodTable.add(new Byte((byte) Code.putstatic));
		MethodTable.add(new Byte((byte) (address >> 8)));
		MethodTable.add(new Byte((byte) address));
	}

	void addNameTerminator() {
		addWordToStaticData(-1, Code.dataSize++);
	}

	public void addTableTerminator() {
		addWordToStaticData(-2, Code.dataSize++);
	}

	void addFunctionAddress(int functionAddress) {
		addWordToStaticData(functionAddress, Code.dataSize++);
	}

	public void addFunctionEntry(String name, int functionAddressInCodeBuffer) {
		for (int j = 0; j < name.length(); j++) {
			addWordToStaticData((int) (name.charAt(j)), Code.dataSize++);
		}
		addNameTerminator();
		addFunctionAddress(functionAddressInCodeBuffer);
	}

	public void copyTable() {
		Object ia[]=MethodTable.toArray();
		for (int i=0; i<ia.length; i++)
		Code.buf[Code.pc++]=((Byte)ia[i]).byteValue();
		MethodTable.clear();
	}
}
