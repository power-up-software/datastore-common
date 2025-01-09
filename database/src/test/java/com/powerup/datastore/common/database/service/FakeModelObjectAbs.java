package com.powerup.datastore.common.database.service;

import com.powerup.java.immutable.model.ModelObjectAbs;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class FakeModelObjectAbs extends ModelObjectAbs {
    private FakeModelObjectAbs(final Builder builder) {
        super(builder);
    }

    // BEGIN_GENERATED_CODE

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FakeModelObjectAbs that = (FakeModelObjectAbs) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getId(), that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getId())
                .toHashCode();
    }

    // END_GENERATED_CODE

    public static final class Builder extends ModelObjectAbs.Builder<FakeModelObjectAbs> {

        @Override
        public FakeModelObjectAbs build() throws IllegalStateException {
            return new FakeModelObjectAbs(this);
        }
    }
}