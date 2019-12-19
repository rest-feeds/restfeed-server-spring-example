package org.restfeeds.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.StringJoiner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FeedItem {

  @Id
  @JsonIgnore
  private long offset;

  private String id;
  private transient String next;
  private String type;
  private String uri;
  private String method;
  private String timestamp;
  private Object data;

  public long getOffset() {
    return offset;
  }

  public void setOffset(long offset) {
    this.offset = offset;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FeedItem.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("next='" + next + "'")
        .add("type='" + type + "'")
        .add("uri='" + uri + "'")
        .add("method='" + method + "'")
        .add("timestamp='" + timestamp + "'")
        .add("data=" + data)
        .toString();
  }

}
