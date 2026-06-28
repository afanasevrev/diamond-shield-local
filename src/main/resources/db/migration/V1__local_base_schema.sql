CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE local_config_state (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    local_server_id UUID NOT NULL,
    object_id UUID,

    last_config_pull_at TIMESTAMP,
    last_successful_push_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_controllers (
    id UUID PRIMARY KEY,

    name VARCHAR(255),
    model VARCHAR(100),
    serial_number VARCHAR(100),

    ip_address VARCHAR(50),
    port INTEGER,

    status VARCHAR(50),

    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_readers (
    id UUID PRIMARY KEY,

    controller_id UUID,

    name VARCHAR(255),
    reader_type VARCHAR(50),
    direction VARCHAR(20),

    -- На следующих этапах желательно заполнить это поле из центральной конфигурации.
    -- Оно нужно, чтобы локальный сервер понимал, к какой точке прохода относится считыватель.
    access_point_id UUID,

    status VARCHAR(50),

    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_access_points (
    id UUID PRIMARY KEY,

    controller_id UUID,
    zone_from_id UUID,
    zone_to_id UUID,

    name VARCHAR(255),
    access_point_type VARCHAR(50),

    active BOOLEAN NOT NULL DEFAULT true,

    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_persons (
    id UUID PRIMARY KEY,

    person_type VARCHAR(50),
    personnel_number VARCHAR(50),

    last_name VARCHAR(100),
    first_name VARCHAR(100),
    middle_name VARCHAR(100),

    active BOOLEAN NOT NULL DEFAULT true,

    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_access_identifiers (
    id UUID PRIMARY KEY,

    person_id UUID NOT NULL,

    identifier_type VARCHAR(50) NOT NULL,
    identifier_value_hash TEXT NOT NULL,

    valid_from TIMESTAMP,
    valid_to TIMESTAMP,

    status VARCHAR(50) NOT NULL,

    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_access_rules (
    id UUID PRIMARY KEY,

    person_id UUID NOT NULL,
    access_point_id UUID NOT NULL,
    schedule_id UUID,

    valid_from TIMESTAMP,
    valid_to TIMESTAMP,

    active BOOLEAN NOT NULL DEFAULT true,

    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_schedules (
    id UUID PRIMARY KEY,

    name VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT true,

    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE local_schedule_intervals (
    id UUID PRIMARY KEY,

    schedule_id UUID NOT NULL,

    day_of_week INTEGER NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,

    updated_at TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT chk_local_schedule_interval_day CHECK (day_of_week BETWEEN 1 AND 7),
    CONSTRAINT chk_local_schedule_interval_time CHECK (start_time < end_time)
);

CREATE TABLE local_access_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    local_event_id VARCHAR(100) NOT NULL,

    object_id UUID,
    access_point_id UUID,
    reader_id UUID,
    controller_id UUID,

    person_id UUID,
    identifier_id UUID,

    event_time TIMESTAMP NOT NULL,
    direction VARCHAR(20),

    access_result VARCHAR(50) NOT NULL,
    reason VARCHAR(255),

    identifier_type VARCHAR(50),
    identifier_masked VARCHAR(100),
    identifier_value_hash TEXT,

    is_unknown_identifier BOOLEAN NOT NULL DEFAULT false,

    sent_to_central BOOLEAN NOT NULL DEFAULT false,
    sent_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT uq_local_access_events_local_event_id UNIQUE(local_event_id)
);

CREATE TABLE local_alarm_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    local_event_id VARCHAR(100) NOT NULL,

    object_id UUID,
    access_point_id UUID,
    reader_id UUID,
    controller_id UUID,

    alarm_type VARCHAR(100) NOT NULL,
    severity VARCHAR(50) NOT NULL,
    message TEXT,

    occurred_at TIMESTAMP NOT NULL,

    sent_to_central BOOLEAN NOT NULL DEFAULT false,
    sent_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT uq_local_alarm_events_local_event_id UNIQUE(local_event_id)
);

CREATE TABLE local_device_status_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    object_id UUID,

    device_type VARCHAR(50) NOT NULL,
    device_id UUID NOT NULL,

    status VARCHAR(50) NOT NULL,
    message TEXT,

    sent_to_central BOOLEAN NOT NULL DEFAULT false,
    sent_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT now()
);