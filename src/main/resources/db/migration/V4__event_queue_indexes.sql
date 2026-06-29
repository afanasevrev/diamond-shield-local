CREATE INDEX IF NOT EXISTS idx_local_access_events_sent_to_central
ON local_access_events(sent_to_central);

CREATE INDEX IF NOT EXISTS idx_local_access_events_created_at
ON local_access_events(created_at);

CREATE INDEX IF NOT EXISTS idx_local_alarm_events_sent_to_central
ON local_alarm_events(sent_to_central);

CREATE INDEX IF NOT EXISTS idx_local_alarm_events_created_at
ON local_alarm_events(created_at);

CREATE INDEX IF NOT EXISTS idx_local_device_status_events_sent_to_central
ON local_device_status_events(sent_to_central);

CREATE INDEX IF NOT EXISTS idx_local_device_status_events_created_at
ON local_device_status_events(created_at);