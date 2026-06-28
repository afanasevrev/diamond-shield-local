CREATE INDEX idx_local_config_state_local_server_id
    ON local_config_state(local_server_id);

CREATE INDEX idx_local_controllers_status
    ON local_controllers(status);

CREATE INDEX idx_local_readers_controller_id
    ON local_readers(controller_id);

CREATE INDEX idx_local_readers_access_point_id
    ON local_readers(access_point_id);

CREATE INDEX idx_local_access_points_controller_id
    ON local_access_points(controller_id);

CREATE INDEX idx_local_access_points_active
    ON local_access_points(active);

CREATE INDEX idx_local_persons_active
    ON local_persons(active);

CREATE INDEX idx_local_identifiers_person_id
    ON local_access_identifiers(person_id);

CREATE INDEX idx_local_identifiers_type_hash
    ON local_access_identifiers(identifier_type, identifier_value_hash);

CREATE INDEX idx_local_identifiers_status
    ON local_access_identifiers(status);

CREATE INDEX idx_local_rules_person_id
    ON local_access_rules(person_id);

CREATE INDEX idx_local_rules_access_point_id
    ON local_access_rules(access_point_id);

CREATE INDEX idx_local_rules_person_point
    ON local_access_rules(person_id, access_point_id);

CREATE INDEX idx_local_rules_active
    ON local_access_rules(active);

CREATE INDEX idx_local_schedule_intervals_schedule_id
    ON local_schedule_intervals(schedule_id);

CREATE INDEX idx_local_schedule_intervals_schedule_day
    ON local_schedule_intervals(schedule_id, day_of_week);

CREATE INDEX idx_local_access_events_sent
    ON local_access_events(sent_to_central);

CREATE INDEX idx_local_access_events_event_time
    ON local_access_events(event_time);

CREATE INDEX idx_local_access_events_access_point_id
    ON local_access_events(access_point_id);

CREATE INDEX idx_local_access_events_person_id
    ON local_access_events(person_id);

CREATE INDEX idx_local_alarm_events_sent
    ON local_alarm_events(sent_to_central);

CREATE INDEX idx_local_alarm_events_occurred_at
    ON local_alarm_events(occurred_at);

CREATE INDEX idx_local_device_status_events_sent
    ON local_device_status_events(sent_to_central);

CREATE INDEX idx_local_device_status_events_device
    ON local_device_status_events(device_type, device_id);