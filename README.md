# Format
A library for reading and writing to any form of stream. Classic example is to read and write to a file

This deals only with byte arrays when reading and writing to prevent any issues when reading using different encoding sets.

## Usage
Using the classic file read and write example as it's easiest to visualise

### Record Configuration
First thing to do is to create a definition of the record (or line). This defines the record type (e.g. delimited), the specific details about the record type, and the definition of the fields within the record

### Record
A record is the POJO for a stream record (e.g. the POJO for a line in a flat file). This contains the list of fields that make up the record. Each field will have its byte array representation, and a formatted to be used to extract the field value correctly. The field value should only be manipulated from the field using the utility methods FieldUtil

### Reading
A set of utility methods have been created for reading a stream, one of which is reading from a file. This will ensure that the streams are closed correctly (unless the stream method is called as that is then the responsibility of the caller). The only parameters required for reading a stream is the stream to read from itself, and the Record Configuration for the stream. This is useful to be used when reading from a stream that has only 1 type of record. If there are multiple then the record reading utilities should be used. This will then create a Record object per record.

### Writing
A set of utility methods have been created for writing to a stream, one of which is writing to a file. This will ensure that the streams are closed correctly (unless the stream method is called as that is then the responsibility of the caller). The only parameters required for writing to a stream is the stream to write to itself and the Records to write.
