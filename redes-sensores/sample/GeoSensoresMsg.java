/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'GeoSensoresMsg'
 * message type.
 */

public class GeoSensoresMsg extends net.tinyos.message.Message {

    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 10;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = 6;

    /** Create a new GeoSensoresMsg of size 10. */
    public GeoSensoresMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new GeoSensoresMsg of the given data_length. */
    public GeoSensoresMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new GeoSensoresMsg with the given data_length
     * and base offset.
     */
    public GeoSensoresMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new GeoSensoresMsg using the given byte array
     * as backing store.
     */
    public GeoSensoresMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new GeoSensoresMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public GeoSensoresMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new GeoSensoresMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public GeoSensoresMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new GeoSensoresMsg embedded in the given message
     * at the given base offset.
     */
    public GeoSensoresMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new GeoSensoresMsg embedded in the given message
     * at the given base offset and length.
     */
    public GeoSensoresMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <GeoSensoresMsg> \n";
      try {
        s += "  [msgid=0x"+Long.toHexString(get_msgid())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [nodeid=0x"+Long.toHexString(get_nodeid())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [type=0x"+Long.toHexString(get_type())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [timesleep=0x"+Long.toHexString(get_timesleep())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [timeawake=0x"+Long.toHexString(get_timeawake())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: msgid
    //   Field type: int, unsigned
    //   Offset (bits): 0
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'msgid' is signed (false).
     */
    public static boolean isSigned_msgid() {
        return false;
    }

    /**
     * Return whether the field 'msgid' is an array (false).
     */
    public static boolean isArray_msgid() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'msgid'
     */
    public static int offset_msgid() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'msgid'
     */
    public static int offsetBits_msgid() {
        return 0;
    }

    /**
     * Return the value (as a int) of the field 'msgid'
     */
    public int get_msgid() {
        return (int)getUIntBEElement(offsetBits_msgid(), 16);
    }

    /**
     * Set the value of the field 'msgid'
     */
    public void set_msgid(int value) {
        setUIntBEElement(offsetBits_msgid(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'msgid'
     */
    public static int size_msgid() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'msgid'
     */
    public static int sizeBits_msgid() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: nodeid
    //   Field type: int, unsigned
    //   Offset (bits): 16
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'nodeid' is signed (false).
     */
    public static boolean isSigned_nodeid() {
        return false;
    }

    /**
     * Return whether the field 'nodeid' is an array (false).
     */
    public static boolean isArray_nodeid() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'nodeid'
     */
    public static int offset_nodeid() {
        return (16 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'nodeid'
     */
    public static int offsetBits_nodeid() {
        return 16;
    }

    /**
     * Return the value (as a int) of the field 'nodeid'
     */
    public int get_nodeid() {
        return (int)getUIntBEElement(offsetBits_nodeid(), 16);
    }

    /**
     * Set the value of the field 'nodeid'
     */
    public void set_nodeid(int value) {
        setUIntBEElement(offsetBits_nodeid(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'nodeid'
     */
    public static int size_nodeid() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'nodeid'
     */
    public static int sizeBits_nodeid() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: type
    //   Field type: int, unsigned
    //   Offset (bits): 32
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'type' is signed (false).
     */
    public static boolean isSigned_type() {
        return false;
    }

    /**
     * Return whether the field 'type' is an array (false).
     */
    public static boolean isArray_type() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'type'
     */
    public static int offset_type() {
        return (32 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'type'
     */
    public static int offsetBits_type() {
        return 32;
    }

    /**
     * Return the value (as a int) of the field 'type'
     */
    public int get_type() {
        return (int)getUIntBEElement(offsetBits_type(), 16);
    }

    /**
     * Set the value of the field 'type'
     */
    public void set_type(int value) {
        setUIntBEElement(offsetBits_type(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'type'
     */
    public static int size_type() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'type'
     */
    public static int sizeBits_type() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: timesleep
    //   Field type: int, unsigned
    //   Offset (bits): 48
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'timesleep' is signed (false).
     */
    public static boolean isSigned_timesleep() {
        return false;
    }

    /**
     * Return whether the field 'timesleep' is an array (false).
     */
    public static boolean isArray_timesleep() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'timesleep'
     */
    public static int offset_timesleep() {
        return (48 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'timesleep'
     */
    public static int offsetBits_timesleep() {
        return 48;
    }

    /**
     * Return the value (as a int) of the field 'timesleep'
     */
    public int get_timesleep() {
        return (int)getUIntBEElement(offsetBits_timesleep(), 16);
    }

    /**
     * Set the value of the field 'timesleep'
     */
    public void set_timesleep(int value) {
        setUIntBEElement(offsetBits_timesleep(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'timesleep'
     */
    public static int size_timesleep() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'timesleep'
     */
    public static int sizeBits_timesleep() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: timeawake
    //   Field type: int, unsigned
    //   Offset (bits): 64
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'timeawake' is signed (false).
     */
    public static boolean isSigned_timeawake() {
        return false;
    }

    /**
     * Return whether the field 'timeawake' is an array (false).
     */
    public static boolean isArray_timeawake() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'timeawake'
     */
    public static int offset_timeawake() {
        return (64 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'timeawake'
     */
    public static int offsetBits_timeawake() {
        return 64;
    }

    /**
     * Return the value (as a int) of the field 'timeawake'
     */
    public int get_timeawake() {
        return (int)getUIntBEElement(offsetBits_timeawake(), 16);
    }

    /**
     * Set the value of the field 'timeawake'
     */
    public void set_timeawake(int value) {
        setUIntBEElement(offsetBits_timeawake(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'timeawake'
     */
    public static int size_timeawake() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'timeawake'
     */
    public static int sizeBits_timeawake() {
        return 16;
    }

}
