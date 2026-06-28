ALTER TABLE local_readers
ADD COLUMN IF NOT EXISTS access_point_id UUID;

CREATE INDEX IF NOT EXISTS idx_local_readers_access_point_id
    ON local_readers(access_point_id);