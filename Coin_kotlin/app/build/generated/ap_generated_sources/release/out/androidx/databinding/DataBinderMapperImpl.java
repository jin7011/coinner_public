package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.coinner.coin_kotlin.DataBinderMapperImpl());
  }
}
