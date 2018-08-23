package com.example.kos.mysecrect.utils;

import java.util.List;

public interface RxSyncList<T> {

  void add(T t);

  void add(List<T> tList);

  void set(T t, int pos);

  void remove(T t);

  void remove(int index);

  void set(List<T> tList);

  void clear();

  int getItemCount();

  T getItem(int position);
}
