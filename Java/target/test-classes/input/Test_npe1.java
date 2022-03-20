package org.csu.input;

import com.google.javascript.jscomp.newtypes.JSType;
import com.google.javascript.rhino.jstype.UnionTypeBuilder;

import java.util.List;

class Test_npe1 {

    JSType meet(JSType that) {
      UnionTypeBuilder builder = new UnionTypeBuilder(registry);
      for (int i = 0; i < alternatesWithoutStucturalTyping.size(); i++) {
        JSType alternate = alternatesWithoutStucturalTyping.get(i);
        if (alternate.isSubtype(that)) {
          builder.addAlternate(alternate);
        }
      }

      if (that.isUnionType()) {
        List<JSType> thoseAlternatesWithoutStucturalTyping =
            that.toMaybeUnionType().alternatesWithoutStucturalTyping;
        for (int i = 0; i < thoseAlternatesWithoutStucturalTyping.size(); i++) {
          JSType otherAlternate = thoseAlternatesWithoutStucturalTyping.get(i);
          if (otherAlternate.isSubtype(this)) {
            builder.addAlternate(otherAlternate);
          }
        }
      } else if (that.isSubtype(this)) {
        builder.addAlternate(that);
      }
      JSType result = builder.build();
      if (!result.isNoType()) {
        return result;
      } else if (this.isObject() && (that.isObject() && !that.isNoType())) {
        return getNativeType(JSTypeNative.NO_OBJECT_TYPE);
      } else {
        return getNativeType(JSTypeNative.NO_TYPE);
      }
    }
}