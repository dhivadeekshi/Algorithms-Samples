class FlowEdge:

    FLOATING_POINT_EPSILON = 1e-10

    def __init__(self, v, w, capacity):
        self.v = v
        self.w = w
        self.capacity = capacity
        self.flow = 0.0

    def from_v(self):
        return self.v

    def to_v(self):
        return self.w

    def other(self, v):
        if self.v == v:
            return self.w
        elif self.w == v:
            return self.v
        else:
            raise ValueError

    def add_residual_capacity_to(self, v, delta):
        if delta < 0.0:
            raise ValueError

        if self.v == v:
            self.flow -= delta
        elif self.w == v:
            self.flow += delta
        else:
            raise ValueError

        if abs(self.flow) <= self.FLOATING_POINT_EPSILON:
            self.flow = 0.0
        if abs(self.flow - self.capacity) <= self.FLOATING_POINT_EPSILON:
            self.flow = self.capacity

    def residual_capacity_to(self, v):
        if self.w == v:
            return self.capacity - self.flow
        elif self.v == v:
            return self.flow
        else:
            raise ValueError

    def __str__(self):
        return f'{self.v} -> {self.w}, {self.flow}/{self.capacity}'
