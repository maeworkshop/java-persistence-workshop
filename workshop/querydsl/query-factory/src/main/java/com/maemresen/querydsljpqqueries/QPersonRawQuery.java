package com.maemresen.querydsljpqqueries;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.RelationalPathBase;
import java.io.Serial;
import java.sql.Types;
import lombok.EqualsAndHashCode;

public class QPersonRawQuery extends RelationalPathBase<QPersonRawQuery> {

  public static final QPersonRawQuery person = new QPersonRawQuery("person");

  @Serial
  private static final long serialVersionUID = 1L;

  public final NumberPath<Long> id = createNumber("id", Long.class);
  public final StringPath firstName = createString("firstName");
  public final StringPath lastName = createString("lastName");

  public QPersonRawQuery(final String variable) {
    super(QPersonRawQuery.class, forVariable(variable), "public", "person");
    addMetadata();
  }

  private void addMetadata() {
    int idx = 0;
    addMetadata(id, ColumnMetadata.named("id").withIndex(++idx).ofType(Types.NUMERIC));
    addMetadata(firstName, ColumnMetadata.named("first_name").withIndex(++idx).ofType(Types.VARCHAR));
    addMetadata(lastName, ColumnMetadata.named("last_name").withIndex(++idx).ofType(Types.VARCHAR));
  }
}
