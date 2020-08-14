package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.bses.dinesh.dsk.DataBinderMapperImpl());
  }
}
