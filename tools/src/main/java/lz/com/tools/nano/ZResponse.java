// Generated by the protocol buffer compiler.  DO NOT EDIT!

package lz.com.tools.nano;

@SuppressWarnings("hiding")
public final class ZResponse extends
    com.google.protobuf.nano.android.ParcelableMessageNano {

  // Used by Parcelable
  @SuppressWarnings({"unused"})
  public static final android.os.Parcelable.Creator<ZResponse> CREATOR =
      new com.google.protobuf.nano.android.ParcelableMessageNanoCreator<
          ZResponse>(ZResponse.class);

  private static volatile ZResponse[] _emptyArray;
  public static ZResponse[] emptyArray() {
    // Lazily initializes the empty array
    if (_emptyArray == null) {
      synchronized (
          com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
        if (_emptyArray == null) {
          _emptyArray = new ZResponse[0];
        }
      }
    }
    return _emptyArray;
  }

  // optional bytes ZPack = 1;
  public byte[] zPack;

  // optional bool IsSuccess = 2 [default = false];
  public boolean isSuccess;

  // optional string ExceptionMessage = 3;
  public java.lang.String exceptionMessage;

  public ZResponse() {
    clear();
  }

  public ZResponse clear() {
    zPack = com.google.protobuf.nano.WireFormatNano.EMPTY_BYTES;
    isSuccess = false;
    exceptionMessage = "";
    cachedSize = -1;
    return this;
  }

  @Override
  public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
      throws java.io.IOException {
    if (!java.util.Arrays.equals(this.zPack, com.google.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
      output.writeBytes(1, this.zPack);
    }
    if (this.isSuccess != false) {
      output.writeBool(2, this.isSuccess);
    }
    if (!this.exceptionMessage.equals("")) {
      output.writeString(3, this.exceptionMessage);
    }
    super.writeTo(output);
  }

  @Override
  protected int computeSerializedSize() {
    int size = super.computeSerializedSize();
    if (!java.util.Arrays.equals(this.zPack, com.google.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeBytesSize(1, this.zPack);
    }
    if (this.isSuccess != false) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeBoolSize(2, this.isSuccess);
    }
    if (!this.exceptionMessage.equals("")) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeStringSize(3, this.exceptionMessage);
    }
    return size;
  }

  @Override
  public ZResponse mergeFrom(
          com.google.protobuf.nano.CodedInputByteBufferNano input)
      throws java.io.IOException {
    while (true) {
      int tag = input.readTag();
      switch (tag) {
        case 0:
          return this;
        default: {
          if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
            return this;
          }
          break;
        }
        case 10: {
          this.zPack = input.readBytes();
          break;
        }
        case 16: {
          this.isSuccess = input.readBool();
          break;
        }
        case 26: {
          this.exceptionMessage = input.readString();
          break;
        }
      }
    }
  }

  public static ZResponse parseFrom(byte[] data)
      throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
    return com.google.protobuf.nano.MessageNano.mergeFrom(new ZResponse(), data);
  }

  public static ZResponse parseFrom(
          com.google.protobuf.nano.CodedInputByteBufferNano input)
      throws java.io.IOException {
    return new ZResponse().mergeFrom(input);
  }
}
