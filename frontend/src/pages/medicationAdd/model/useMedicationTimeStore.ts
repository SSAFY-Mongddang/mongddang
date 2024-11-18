import { create } from 'zustand';

type MedicationTimeType = {
  timeFields: { id: number; hour: number; minute: number }[];
  addTimeField: () => void;
  deleteTimeField: () => void;
  setTimeField: (id: number, field: string, value: number) => void;
  resetTimeField: () => void;
};

export const useMedicationTimeStore = create<MedicationTimeType>((set) => ({
  timeFields: [{ id: 0, hour: 0, minute: 0 }],
  addTimeField: () => {
    set((state) => ({
      timeFields: [
        ...state.timeFields,
        { id: state.timeFields.length + 1, hour: 0, minute: 0 },
      ],
    }));
  },

  deleteTimeField: () => {
    set((state) => ({
      timeFields:
        state.timeFields.length > 1
          ? state.timeFields.slice(0, -1)
          : state.timeFields,
    }));
  },

  setTimeField: (id: number, field: string, value: number) => {
    console.log(id, field, value);
    set((state) => ({
      timeFields: state.timeFields.map((item) =>
        item.id === id
          ? {
              ...item,
              [field]: value,
            }
          : item
      ),
    }));
  },
  resetTimeField: () =>
    set({
      timeFields: [{ id: 0, hour: 0, minute: 0 }],
    }),
}));
