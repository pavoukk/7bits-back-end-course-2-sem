ALTER TABLE task ADD COLUMN updated_at TIMESTAMPTZ;
UPDATE task SET updated_at = created_at WHERE updated_at IS NULL;
ALTER TABLE task ALTER COLUMN updated_at SET NOT NULL;
